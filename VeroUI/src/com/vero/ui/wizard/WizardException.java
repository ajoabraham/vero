package com.vero.ui.wizard;

public class WizardException extends Exception {

    public WizardException() {
    }

    public WizardException(String message) {
        super(message);
    }

    public WizardException(Throwable cause) {
        super(cause);
    }

    public WizardException(String message, Throwable cause) {
        super(message, cause);
    }

    public WizardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
