package uz.hrmanager.hrmanager.config;

public class SwaggerConstants {

    public static final String CREATE_EMPLOYEE_EXAMPLE = """
            {
              "firstName": "Salomov",
              "lastName": "Anvar",
              "phone": "998910000503",
              "email": "123456",
              "position": "BOSHMUTAXASSIS",
              "department": "DASTURCHILAR"
            }
            """;
    public static final String CREATE_EMPLOYEE_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
              "active": true,
              "department": "DASTURCHILAR",
              "email": "123456",
              "firstName": "Salomov",
              "hireDate": "2025-11-23",
              "id": 4,
              "lastName": "Anvar",
              "phone": "998910000503",
              "position": "BOSHMUTAXASSIS"
            }
            """;

    public static final String UPDATE_EMPLOYEE_EXAMPLE = """ 
            {
              "firstName": "Salomov",
              "lastName": "Anvar",
              "phone": "998911111111",
              "email": "123456",
              "position": "KATTAMUTAXASSIS",
              "department": "MARKETING"
            }
            """;

    public static final String UPDATE_EMPLOYEE_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
              "active": null,
              "department": "MARKETING",
              "email": "123456",
              "firstName": "Salomov",
              "hireDate": "2025-11-23",
              "id": 4,
              "lastName": "Anvar",
              "phone": "998911111111",
              "position": "KATTAMUTAXASSIS"
            }
            """;

    public static final String GET_EMPLOYEE_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
              "id": 4,
              "firstName": "Salomov",
              "lastName": "Anvar",
              "phone": "998911111111",
              "email": "123456",
              "position": "KATTAMUTAXASSIS",
              "department": "RAHBARIYAT",
              "hireDate": "2025-11-23",
              "active": true
            }
            """;


}
