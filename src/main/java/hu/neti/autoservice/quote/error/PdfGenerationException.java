package hu.neti.autoservice.quote.error;

public class PdfGenerationException extends RuntimeException {
    public PdfGenerationException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

    public PdfGenerationException(String errorMessage){
        super(errorMessage);
    }

    public PdfGenerationException(Throwable error){
        super(error);
    }
}
