FROM ubuntu:latest
LABEL authors="mailm"

ENTRYPOINT ["top", "-b"]