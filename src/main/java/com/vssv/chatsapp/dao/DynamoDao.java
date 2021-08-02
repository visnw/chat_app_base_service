package com.vssv.chatsapp.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedTimestamp;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.vssv.chatsapp.utils.BaseUtils;

import java.util.*;

public class DynamoDao {

    private DynamoDB dynamoDB;
    public static DynamoDao instance;

    private DynamoDao(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDB = new DynamoDB(client);
    }

    public static DynamoDao getInstance(){
        if(instance == null){
           instance = new DynamoDao();
        }
        return instance;
    }

    public List<Map<String, Object>> getData(String tableName, Object query){

        Table table = dynamoDB.getTable(tableName);
        List<Map<String, Object>> response = new ArrayList<>();
        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":xxx", query);
        QuerySpec querySpec = new QuerySpec()
                    .withKeyConditionExpression("#user_id = :xxx")
                .withNameMap(new NameMap().with("#user_id", "user_id"))
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        try {
            System.out.println("Product with the user_id " + query);
            items = table.query(querySpec);
            iterator = items.iterator();

            while (iterator.hasNext()) {
                item = iterator.next();
                Map<String, Object> record = BaseUtils.convertJsonToMap(item.toJSON());
                response.add(record);
                System.out.println(item.toJSON());
            }
        } catch (Exception e) {
            System.err.println("Cannot find products with the ID number 122");
            System.err.println(e.getMessage());
        }
        return response;
    }

    public void addData(String tableName, String primaryKey, Map<String, Object> data){
        Item item = new Item();
        data.forEach((key, value) -> {
            if(key.equals(primaryKey)){
                item.withPrimaryKey(String.valueOf(key), String.valueOf(value));
            } if(key.equals("date")) {
                item.with("date", getCreatedDate().toString());
            } else {
                item.with(String.valueOf(key), String.valueOf(value));
            }
        });
        if(!data.isEmpty()){
            System.out.println("Table Name : " + tableName);
            System.out.println("Data : " + data);
            Table table = dynamoDB.getTable(tableName);
            table.putItem(item);
        }
    }

    @DynamoDBTypeConvertedTimestamp(pattern="yyyyMMddHHmmssSSS", timeZone="UTC")
    public Date getCreatedDate(){
        return new Date();
    }

}
