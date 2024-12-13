---
description: This page shows you how to make custom player data
---
# Custom Player Data
Creating Custom Player Data is not really easy, but it's not that hard either.
{% hint style="warning" %}
While joining the server, please wait till the server is done loading, otherwise the custom player data will not work!
{% endhint %}
## Creating the Custom Player Data
Create your custom player data class that implements `ICustomPlayerData`. In this example, we shall create a class called `CustomPlayerDataExample`That will just hold a simple `String` value.
```java
public class CustomPlayerDataExample implements ICustomPlayerData {}
```
Next, we'll implement the following methods: `getId`, `deserialize`and `serialize`&#x20;
```java
public class CustomPlayerDataExample implements ICustomPlayerData {
    @Override
    public String getId() {
        return "";
    }
    @Override
    public ICustomPlayerData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
    @Override
    public JsonElement serialize(ICustomPlayerData playerData, Type type, JsonSerializationContext context) {
        return null;
    }
}
```
Now we'll make a String variable and give it a value. And create getter and setters for it.
```java
public class CustomPlayerDataExample implements ICustomPlayerData {
    private String sexy;
    // Recommended to have a constructor with no parameters
    // But optional, of course :)
    public CustomPlayerDataExample() {
        this.sexy = "sexy";
    }
    @Override
    public String getId() {
        return "custom_player_data_example";
    }
    
    public String getSexy() {
        return sexy;
    }
    public void setSexy(String sexy) {
        this.sexy = sexy;
    }
    @Override
    public ICustomPlayerData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
    @Override
    public JsonElement serialize(ICustomPlayerData playerData, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
```
Finally, we'll implement the logic for serialization and deserialization, this NEEDS to be done otherwise it won't save to the database properly.
```java
public class CustomPlayerDataExample implements ICustomPlayerData {
    private String sexy;
    // Recommended to have a constructor with no parameters
    // But optional, of course :)
    public CustomPlayerDataExample() {
        this.sexy = "sexy";
    }
    @Override
    public String getId() {
        return "custom_player_data_example";
    }
    public String getSexy() {
        return sexy;
    }
    public void setSexy(String sexy) {
        this.sexy = sexy;
    }
    @Override
    public ICustomPlayerData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        CustomPlayerDataExample customPlayerDataExample = new CustomPlayerDataExample();
        // Getting the value of the key "sexy" from the json object
        customPlayerDataExample.setSexy(object.get("sexy").getAsString());
        return customPlayerDataExample;
    }
    @Override
    public JsonElement serialize(ICustomPlayerData playerData, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        // Adding the key "sexy" with the value of the "sexy" field
        object.addProperty("sexy", sexy);
        return object;
    }
}
```
And that's it! Now onto registering it!
## Registering the Custom Player Data
```java
// Accessing the API
SkyWarsAPI api = SkyWarsProvider.get();
// Registering the custom player data
api.registerCustomPlayerData(new CustomPlayerDataExample());
```