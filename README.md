eDMoee

1.使用Eclipse編寫檔案，需安裝Tomcat9

2.會使用到MySQL資料庫，修改資料庫用密碼的檔案在\eDMoee\src\main\java\_00_init\util\DBService.java
public static final String PSWD_MySQL = "Do!ng123";
和\eDMoee\src\main\webapp\META-INF\context.xml的這一段 password="Do!ng123"
建立練習用表格的檔案在\eDMoee\src\main\java\_00_init\EDMTableReset.java
直接在Eclipse執行