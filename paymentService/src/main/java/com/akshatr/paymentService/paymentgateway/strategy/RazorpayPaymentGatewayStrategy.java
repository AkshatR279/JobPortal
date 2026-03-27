package com.akshatr.paymentService.paymentgateway.strategy;

import com.akshatr.paymentService.config.PaymentGatewayConfig;
import com.akshatr.paymentService.model.dto.integration.PaymentGatewayStatus;
import com.akshatr.paymentService.model.dto.integration.PaymentGatewayWebhook;
import com.akshatr.paymentService.model.dto.integration.RazorpayWebhook;
import com.akshatr.paymentService.model.dto.order.OrderResponseDto;
import com.akshatr.paymentService.model.dto.user.UserResponseDto;
import com.akshatr.paymentService.model.entity.Payment;
import com.akshatr.paymentService.model.enums.PaymentGateway;
import com.akshatr.paymentService.model.enums.PaymentStatus;
import com.akshatr.paymentService.model.utilmodel.Credentials;
import com.akshatr.paymentService.service.OrderService;
import com.akshatr.paymentService.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RazorpayPaymentGatewayStrategy extends PaymentGatewayStrategy{
    private final PaymentGatewayConfig config;
    private final UserService userService;
    private final OrderService orderService;

    @Override
    public PaymentGateway getPaymentGateway() {
        return PaymentGateway.RAZORPAY;
    }

    @Override
    public String generatePaymentLink(Payment payment) {
        try {
            Credentials credentials = config.getCredentials(PaymentGateway.RAZORPAY);
            PaymentLink paymentLinkObj = generateRazorPayLink(credentials, payment);

            String paymentLink = paymentLinkObj.get("short_url");
            if(paymentLink==null || paymentLink.isBlank()){
                throw new RuntimeException("Failed to generate payment gateway link.");
            }

            /*
            SUCCESS =>
            {
                "accept_partial": true,
                    "amount": 1000,
                    "amount_paid": 0,
                    "callback_method": "get",
                    "callback_url": "https://example-callback-url.com/",
                    "cancelled_at": 0,
                    "created_at": 1591097057,
                    "currency": "<currency>",
                    "customer": {
                "contact": "<phone>",
                        "email": "<email>",
                        "name": "<name>"
            },
                "description": "Payment for policy no #23456",
                    "expire_by": 1691097057,
                    "expired_at": 0,
                    "first_min_partial_amount": 100,
                    "id": "plink_ExjpAUN3gVHrPJ",
                    "notes": {
                "policy_name": "Jeevan Bima"
            },
                "notify": {
                "email": true,
                        "sms": true
            },
                "payments": null,
                    "reference_id": "TS1989",
                    "reminder_enable": true,
                    "reminders": [],
                "short_url": "https://rzp.io/i/nxrHnLJ",
                    "status": "created",
                    "updated_at": 1591097057,
                    "user_id": ""
            }

            FAILURE =>
            {
              "error": {
                "code": "BAD_REQUEST_ERROR",
                "description": "The api key provided is invalid",
                "source": "NA",
                "step": "NA",
                "reason": "NA",
                "metadata": {}
              }
            }
             */

            return  paymentLink;
        }
        catch (RazorpayException ex){
            throw new RuntimeException(ex);
        }
    }

    private PaymentLink generateRazorPayLink(Credentials credentials, Payment payment) throws RazorpayException{
        RazorpayClient razorpayClient = new RazorpayClient(credentials.getKey(), credentials.getSecret());

        int amount = payment.getAmount().intValue()*100;

        UserResponseDto user = userService.findById(payment.getUserId());
        if(user == null){
            throw new RuntimeException("User not found.");
        }

        OrderResponseDto order = orderService.getOrder(payment.getOrderId());
        if(order == null){
            throw new RuntimeException("Order not found.");
        }

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
        //paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by", (System.currentTimeMillis() / 1000) + (10 * 60)); //10 min
        paymentLinkRequest.put("reference_id", payment.getName());
        paymentLinkRequest.put("description", "Payment towards Order:" + order.getName());

        JSONObject customer = new JSONObject();
        customer.put("name", user.getName());
        //customer.put("contact", "");
        //customer.put("email", "");
        paymentLinkRequest.put("customer",customer);

//        JSONObject notify = new JSONObject();
//        notify.put("sms", false);
//        notify.put("email", false);
//        paymentLinkRequest.put("notify",notify);
//        paymentLinkRequest.put("reminder_enable",false);

        JSONObject notes = new JSONObject();
        notes.put("paymentId", payment.getName());
        paymentLinkRequest.put("notes",notes);

        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");

        return razorpayClient.paymentLink.create(paymentLinkRequest);
    }

    @Override
    public boolean verifyWebhook(PaymentGatewayWebhook webhook){
        try {
            RazorpayWebhook razorpayWebhook = (RazorpayWebhook) webhook.getWebhookData();
            return verifyWebhookSignature(razorpayWebhook.getPayload(), razorpayWebhook.getSignature());
        }
        catch (RazorpayException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    private boolean verifyWebhookSignature(String payload, String actualSignature) throws RazorpayException {
        return Utils.verifyWebhookSignature(payload, actualSignature, config.getCredentials(PaymentGateway.RAZORPAY).getWebhookSecret());
    }

    @Override
    public PaymentGatewayStatus processWebhook(PaymentGatewayWebhook webhook) {
        try {
            RazorpayWebhook razorpayWebhook = (RazorpayWebhook) webhook.getWebhookData();
            String payload = razorpayWebhook.getPayload();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);

            String event = root.get("event").asText();
            JsonNode paymentEntity = root
                    .path("payload")
                    .path("payment")
                    .path("entity");

            String razorPaymentId = paymentEntity.path("id").asText();
            String status = paymentEntity.path("status").asText();

            String paymentId = root.path("payload")
                    .path("payment")
                    .path("entity")
                    .path("notes")
                    .path("paymentId")
                    .asText();

            PaymentGatewayStatus paymentStatus = PaymentGatewayStatus.builder()
                    .paymentNo(paymentId)
                    .build();

            switch (event) {
                case "payment.captured":
                case "payment.link.paid":
                    paymentStatus.setStatus(PaymentStatus.SUCCESS);
                    break;

                case "payment.failed":
                case "payment.link.expired":
                    paymentStatus.setStatus(PaymentStatus.FAIL);
                    break;
            }

            return paymentStatus;
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
