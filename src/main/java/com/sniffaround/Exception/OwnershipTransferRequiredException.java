package com.sniffaround.Exception;

public class OwnershipTransferRequiredException extends RuntimeException {
    public OwnershipTransferRequiredException() {
        super("Transfer ownership before leaving");
    }
}
