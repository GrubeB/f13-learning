package pl.app.common.dto_criteria;

public class DtoRequest implements Dto {
    private String dtoName;

    protected DtoRequest(String dtoName) {
        this.dtoName = dtoName;
    }

    public static DtoRequest of(String className) {
        return new DtoRequest(className);
    }

    @Override
    public String getDtoName() {
        return this.dtoName;
    }

    @Override
    public void setDtoName(String className) {
        this.dtoName = className;
    }
}
