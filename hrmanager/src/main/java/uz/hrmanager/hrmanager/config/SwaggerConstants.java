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

    public static final String GET_BALANCE_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
                "employee": {
                  "active": true,
                  "department": "RAHBARIYAT",
                  "email": "123456",
                  "firstName": "Toshmatov",
                  "hibernateLazyInitializer": {},
                  "hireDate": "2025-11-23",
                  "id": 2,
                  "lastName": "Toshmat",
                  "phone": "998911571237",
                  "position": "RAXBAR"
                },
                "id": 5,
                "leaveType": "KASALLIK",
                "remainingDays": 15,
                "totalDays": 15,
                "usedDays": 0
              }
            """;

    public static final String GET_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
                "employeeFirstName": "Olimjonov",
                "employeeLastName": "Olim",
                "endDate": "2025-11-30",
                "id": 2,
                "leaveType": "YILLIK",
                "managerFullName": "Toshmatov Toshmat",
                "requestContent": "menga 2025 yil 23-noyabr kunidan navbatdagi mehnat ta'tilga chiqishimga ruxsat berishingizni so'rayman",
                "startDate": "2025-11-25",
                "status": "TASDIQLANGAN",
                "totalDays": 6
              }
            """;


    public static final String CREATE_LEAVEREQUEST_EXAMPLE = """ 
            {
              "requestContent": "menga 2025 yil 23-noyabr kunidan navbatdagi mehnat ta'tilga chiqishimga ruxsat berishingizni so'rayman",
              "leaveType": "YILLIK",
              "startDate": "2025-11-25",
              "endDate": "2025-11-30"
            }
            """;

    public static final String CREATE_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
              "employeeFirstName": "O'ktamov",
              "employeeLastName": "Sardor",
              "endDate": "2025-12-10",
              "id": 3,
              "leaveType": "YILLIK",
              "managerFullName": "Toshmatov Toshmat",
              "requestContent": "menga 2025 yil 1-dekabr kunidan navbatdagi mehnat ta'tilga chiqishimga ruxsat berishingizni so'rayman",
              "startDate": "2025-12-01",
              "status": "JARAYONDA",
              "totalDays": 10
            }
            """;

    public static final String CREATE_LISTLEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE = """ 
            [
              {
                "employeeFirstName": "O'ktamov",
                "employeeLastName": "Sardor",
                "endDate": "2025-12-10",
                "id": 3,
                "leaveType": "YILLIK",
                "managerFullName": "Toshmatov Toshmat",
                "requestContent": "menga 2025 yil 23-noyabr kunidan navbatdagi mehnat ta'tilga chiqishimga ruxsat berishingizni so'rayman",
                "startDate": "2025-12-01",
                "status": "JARAYONDA",
                "totalDays": 10
              }
            ]
            """;

    public static final String REJECT_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
              "employeeFirstName": "O'ktamov",
              "employeeLastName": "Sardor",
              "endDate": "2025-11-30",
              "id": 5,
              "leaveType": "YILLIK",
              "managerComment": "Ish xajmi ortganligi sababli ta'til keyinga qoldiriladi",
              "managerFullName": "Toshmatov Toshmat",
              "requestContent": "menga 2025 yil 26-noyabr kunidan navbatdagi mehnat ta'tilga chiqishimga ruxsat berishingizni so'rayman",
              "startDate": "2025-11-26",
              "status": "RAD_ETILGAN",
              "totalDays": 5
            }
            """;

    public static final String APPROVE_LEAVEREQUEST_RESPONSE_SUCCESS_EXAMPLE = """ 
            {
              "employeeFirstName": "O'ktamov",
              "employeeLastName": "Sardor",
              "endDate": "2025-11-30",
              "id": 6,
              "leaveType": "YILLIK",
              "managerComment": null,
              "managerFullName": "Toshmatov Toshmat",
              "requestContent": "menga 2025 yil 26-noyabr kunidan navbatdagi mehnat ta'tilga chiqishimga ruxsat berishingizni so'rayman",
              "startDate": "2025-11-26",
              "status": "TASDIQLANGAN",
              "totalDays": 5
            }
            """;

    public static final String CREATE_ATTANDANCE_EXAMPLE = """ 
            { 
              "workDate": "2025-11-25",
              "inTime": "08:20:00",
              "outTime": "18:05:00"
            }
            """;

    public static final String CREATE_ATTANDANCE_RESPONSE_SUCCESS_EXAMPLE = """ 
            Xodimning ishga kelish/ketish vaqti muvaffaqiyatli belgilandi. Status: ON_TIME
            """;

    public static final String APPRSADASDOWQESDASDMPLE = """ 
            {
              "id": "6"
            }
            """;
}