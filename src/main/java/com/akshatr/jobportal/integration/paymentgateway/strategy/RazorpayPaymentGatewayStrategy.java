package com.akshatr.jobportal.integration.paymentgateway.strategy;

import com.akshatr.jobportal.config.PaymentGatewayConfig;
import com.akshatr.jobportal.model.entity.Payment;
import com.akshatr.jobportal.model.enums.PaymentGateway;
import com.akshatr.jobportal.model.utilmodel.Credentials;
import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RazorpayPaymentGatewayStrategy extends PaymentGatewayStrategy{
    private final PaymentGatewayConfig config;

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
                throw new RuntimeException("Failed to get payment gateway link.");
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
        catch (RazorpayException e){
            throw new RuntimeException(e);
        }
    }

    private PaymentLink generateRazorPayLink(Credentials credentials, Payment payment) throws RazorpayException{
        RazorpayClient razorpayClient = new RazorpayClient(credentials.getKey(), credentials.getSecret());

        int amount = payment.getAmount().intValue()*100;

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
        //paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + 10*60*1000); //10 min
        paymentLinkRequest.put("reference_id", payment.getName());
        paymentLinkRequest.put("description", "Payment towards Order:" + payment.getOrder().getName());

        JSONObject customer = new JSONObject();
        customer.put("name", payment.getUser().getName());
        customer.put("contact", "");
        customer.put("email", "");
        paymentLinkRequest.put("customer",customer);

//        JSONObject notify = new JSONObject();
//        notify.put("sms", false);
//        notify.put("email", false);
//        paymentLinkRequest.put("notify",notify);
//        paymentLinkRequest.put("reminder_enable",false);

//        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Life Insurance Policy");
//        paymentLinkRequest.put("notes",notes);

        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");

        return razorpayClient.paymentLink.create(paymentLinkRequest);
    }
}
