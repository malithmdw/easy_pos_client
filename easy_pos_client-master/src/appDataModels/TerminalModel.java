
package appDataModels;

import serverDataModels.Terminal;


/**
 *
 * @author MalithWanniarachchi
 */
public class TerminalModel {
    private long terminalId;
    private long instituteId;
    private String name;
    private String terminalType;

    // ✅ DTO → Model
    public TerminalModel(Terminal dto) {
        this.terminalId = dto.terminal_id;
        this.instituteId = dto.institute_id;
        this.name = dto.name;
        this.terminalType = dto.terminal_type;
    }

    // ✅ Model → DTO
    public Terminal newTerminalDTO() {
        Terminal dto = new Terminal();
        dto.terminal_id = this.getTerminalId();
        dto.institute_id = this.getInstituteId();
        dto.name = this.getName();
        dto.terminal_type = this.getTerminalType();
        return dto;
    }

    // ✅ Getters & Setters
    public long getTerminalId() { return terminalId; }
    public void setTerminalId(long terminalId) { this.terminalId = terminalId; }

    public long getInstituteId() { return instituteId; }
    public void setInstituteId(long instituteId) { this.instituteId = instituteId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTerminalType() { return terminalType; }
    public void setTerminalType(String terminalType) { this.terminalType = terminalType; }
}
