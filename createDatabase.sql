use oopBigProject;
drop table invoice;
CREATE TABLE product (
    productCode VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    salePrice double,
    importPrice double,
    quantity int DEFAULT 0,
    productType int not null,
    width float,
    height float,
    batteryLife int ,
    resolution float,
    cpu VARCHAR(100),
    ram int(4),
    hardDiskCapacity int 
 );
CREATE TABLE invoice (
    invoiceCode VARCHAR(100)  PRIMARY KEY,
    date  DATETIME DEFAULT NOW(),
    totalSalePrice double default 0,
    totalImportPrice double default 0,
    totalProfit double default 0,
	invoiceType int not null
 );
 
 CREATE TABLE invoiceDetail (
	productCode VARCHAR(100),
    invoiceCode VARCHAR(100),
	quantity int not null,
	salePrice double,
    importPrice double,
    CONSTRAINT fk_1 FOREIGN KEY(productCode) REFERENCES product(productCode),
    CONSTRAINT fk_2 FOREIGN KEY(invoiceCode) REFERENCES invoice(invoiceCode),
     CONSTRAINT pk PRIMARY KEY(productCode,invoiceCode)
 );
 
 