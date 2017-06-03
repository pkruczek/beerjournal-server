package com.beerjournal.infrastructure.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "hiddenEventBuilder")
public class MailModel {
    private final String to;
    private final String subject;
    private final String body;
    private final boolean html;

    public static MailModelBuilder builder() {
        return hiddenEventBuilder()
                .html(false);
    }
}
