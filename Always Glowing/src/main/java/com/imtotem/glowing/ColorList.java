package com.imtotem.glowing;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class ColorList extends AbstractFilter {
    @Override
    public Result filter(LogEvent event) {
        return event == null ? Result.NEUTRAL : isLoggable(event.getMessage().getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return isLoggable(msg.getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return isLoggable(msg);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return msg == null ? Result.NEUTRAL : isLoggable(msg.toString());
    }

    private Result isLoggable(String msg) {
        if (msg != null) {
            msg = msg.toLowerCase();
            if (msg.contains("issued server command:")) {
                if (msg.contains("/glow t2ldl") || msg.contains("/color t2ldl")
                        || msg.contains("/alwaysglowing:glow t2ldl") || msg.contains("/alwaysglowing:color t2ldl")
                        || msg.contains("/glow tool") || msg.contains("/color tool")
                        || msg.contains("/alwaysglowing:glow tool") || msg.contains("/alwaysglowing:color tool")
                        || msg.contains("/glow heal") || msg.contains("/color heal")
                        || msg.contains("/alwaysglowing:glow heal") || msg.contains("/alwaysglowing:color heal")
                        || msg.contains("/glow execute") || msg.contains("/color execute")
                        || msg.contains("/alwaysglowing:glow execute") || msg.contains("/alwaysglowing:color execute")) {
                    return Result.DENY;
                }
            } else if ( msg.contains("im_totem moved too quickly!") ) {
                return Result.DENY;
            }
        }
        return Result.NEUTRAL;
    }
}
