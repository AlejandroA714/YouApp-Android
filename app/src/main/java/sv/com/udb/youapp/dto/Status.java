package sv.com.udb.youapp.dto;

import sv.com.udb.youapp.dto.enums.IStatus;

public class Status {

    private int id;

    private IStatus status;

    public Status(int id, IStatus status) {
        this.id = id;
        this.status = status;
    }

    public Status(IStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IStatus getStatus() {
        return status;
    }

    public void setStatus(IStatus status) {
        this.status = status;
    }
}
