package kz.iitu.springlab.notify;

import org.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("console")
@Order(1)
public class ConsoleNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(ConsoleNotifier.class);

    @Override
    public String send(String message) {
        log.info("CONSOLE >> {}", message);
        return "console: " + message;
    }

    @Override
    public String channel() { return "console"; }
}

@Component("email") // chosen by default
@Order(2)
class EmailNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(EmailNotifier.class);

    @Override
    public String send(String message) {
        log.info("EMAIL >> {}", message);
        return "email: " + message;
    }

    @Override
    public String channel() { return "email"; }
}

@Component("noop")
@Fallback                                  // fallback option (Spring 6.2+)
@Order(99)
class NoopNotifier implements Notifier {

    @Override
    public String send(String message) { return "noop"; }

    @Override
    public String channel() { return "noop"; }
}


@Component("reversed")
@Fallback                                  // fallback option (Spring 6.2+)
@Order(3)
class ReversedNotifier implements Notifier {

    @Override
    public String send(String message) {
        StringBuilder sb = new StringBuilder();
        for (int i = message.length() - 1; i >= 0; i--) {
            sb.append(message.charAt(i));
        }
        return sb.toString();
    }

    @Override
    public String channel() { return "reversed"; }
}
