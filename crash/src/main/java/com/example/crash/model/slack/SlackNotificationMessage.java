package com.example.crash.model.slack;

import java.util.List;

public record SlackNotificationMessage(
    List<Block> blocks
) {

}

