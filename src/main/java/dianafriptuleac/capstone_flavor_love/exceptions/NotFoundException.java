package dianafriptuleac.capstone_flavor_love.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long id) {
        super("Il record con id: " + id + " non è stato trovato!");
    }
}
