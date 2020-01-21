package ru.otus.lib.bee.changelog;

import java.util.ArrayList;
import java.util.List;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

@ChangeLog
public class DatabaseChangelog {

    private static final String GENRE = "genre";
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static final String AND = "$and";
    private static final String MIDDLENAME = "middlename";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String NAME = "name";
    
    @ChangeSet(order = "001", id = "addGenres", author = "dlekhanov")
    public void insertGenres(DB db) {
        DBCollection myCollection = db.getCollection("genres");
        myCollection.insert(
                new BasicDBObject().append(NAME, "Русская классика"),
                new BasicDBObject().append(NAME, "Зарубежная классика"),
                new BasicDBObject().append(NAME, "Фэнтези"), 
                new BasicDBObject().append(NAME, "Фантастика"),
                new BasicDBObject().append(NAME, "Современная проза"),
                new BasicDBObject().append(NAME, "Приключения"), 
                new BasicDBObject().append(NAME, "Мистика"),
                new BasicDBObject().append(NAME, "Публицистическая литература"),
                new BasicDBObject().append(NAME, "Любовные романы"),
                new BasicDBObject().append(NAME, "Наука и образование"),
                new BasicDBObject().append(NAME, "Старинная литература"),
                new BasicDBObject().append(NAME, "Античная литература"),
                new BasicDBObject().append(NAME, "Мифи, легенды, эпос"),
                new BasicDBObject().append(NAME, "Древнерусская литература"));
    }
    
    @ChangeSet(order = "002", id = "addAuthors", author = "dlekhanov")
    public void insertAuthors(DB db) {
        DBCollection myCollection = db.getCollection("authors");
        myCollection.insert(
                new BasicDBObject().append(LASTNAME, "Пушкин").append(FIRSTNAME, "Александр").append(MIDDLENAME, "Сергеевич"),
                new BasicDBObject().append(LASTNAME, "Толстой").append(FIRSTNAME, "Лев").append(MIDDLENAME, "Николаевич"),
                new BasicDBObject().append(LASTNAME, "Достоевский").append(FIRSTNAME, "Федор").append(MIDDLENAME, "Михайлович"),
                new BasicDBObject().append(LASTNAME, "Чехов").append(FIRSTNAME, "Антон").append(MIDDLENAME, "Павлович"),
                new BasicDBObject().append(LASTNAME, "Горький").append(FIRSTNAME, "Максим"),
                new BasicDBObject().append(LASTNAME, "Гоголь").append(FIRSTNAME, "Николай").append(MIDDLENAME, "Васильевич"),
                new BasicDBObject().append(LASTNAME, "Толстой").append(FIRSTNAME, "Алексей").append(MIDDLENAME, "Николаевич"),
                new BasicDBObject().append(LASTNAME, "Тургенев").append(FIRSTNAME, "Иван").append(MIDDLENAME, "Сергеевич"),
                new BasicDBObject().append(LASTNAME, "Лермонтов").append(FIRSTNAME, "Михаил").append(MIDDLENAME, "Юрьевич"),
                new BasicDBObject().append(LASTNAME, "Куприн").append(FIRSTNAME, "Александр").append(MIDDLENAME, "Иванович")               
        );
    }

    @ChangeSet(order = "003", id = "addBooks", author = "dlekhanov")
    public void insertBooks(DB db) {
        DBRef author = getAuthor(db);
        DBRef genre = getGenre(db);
        
        DBCollection myCollection = db.getCollection("books");
        myCollection.insert(
                new BasicDBObject().append(TITLE, "Война и мир").append(AUTHOR, author).append(GENRE, genre),
                new BasicDBObject().append(TITLE, "Анна Каренина").append(AUTHOR, author).append(GENRE, genre),
                new BasicDBObject().append(TITLE, "Воскресенье").append(AUTHOR, author).append(GENRE, genre),
                new BasicDBObject().append(TITLE, "Семейное счастье").append(AUTHOR, author).append(GENRE, genre),
                new BasicDBObject().append(TITLE, "Детство, Отрочество, Юность").append(AUTHOR, author).append(GENRE, genre),
                new BasicDBObject().append(TITLE, "Севастопольские рассказы").append(AUTHOR, author).append(GENRE, genre)                
        );
    }

    private DBRef getGenre(DB db) {
        BasicDBObject query = new BasicDBObject();
        query.put("name", "Русская классика");
        DBObject genre = db.getCollection("genres").findOne(query);
        return new DBRef("genres", genre.get("_id"));
    }

    private DBRef getAuthor(DB db) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<>();
        obj.add(new BasicDBObject(LASTNAME, "Толстой"));
        obj.add(new BasicDBObject(FIRSTNAME, "Лев"));
        andQuery.put(AND, obj);
        DBObject author = db.getCollection("authors").findOne(andQuery);
        return new DBRef("authors", author.get("_id"));
    }
}
