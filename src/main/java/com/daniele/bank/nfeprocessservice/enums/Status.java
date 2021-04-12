package com.daniele.bank.nfeprocessservice.enums;

public enum Status {
    AGUARDANDO_PROCESSAMENTO(1),
    EM_PROCESSAMENTO(2),
    PROCESSADA(3),
    PROCESSADA_COM_ERRO(4);

    private final int statusId;

    Status(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
