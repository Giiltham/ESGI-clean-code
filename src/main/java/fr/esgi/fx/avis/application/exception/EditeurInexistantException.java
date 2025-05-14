package fr.esgi.fx.avis.application.exception;

public class EditeurInexistantException extends RuntimeException {

    public EditeurInexistantException(String message) {
        super(message);
    }

    public EditeurInexistantException() {
        super();
    }

}
