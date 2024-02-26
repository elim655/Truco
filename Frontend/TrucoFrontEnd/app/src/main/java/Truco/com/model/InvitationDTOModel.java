package Truco.com.model;

public class InvitationDTOModel {
    private Long roomId;

    private String sender;

    public InvitationDTOModel(Long roomId, String sender) {
        this.roomId = roomId;
        this.sender = sender;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
