package task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * CS2103T IP
 * AY 23/24 Semester 1
 *
 * <p> An abstract Task class</p>
 *
 * @author Koo Yu Cong
 * @version CS2103T AY 23/24 Sem 1
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Todo.class, name = "Todo"),
        @JsonSubTypes.Type(value = Deadline.class, name = "Deadline"),
        @JsonSubTypes.Type(value = Event.class, name = "Event")
})
public abstract class Task {
    private String description;
    private boolean isDone;

    /**
     * A constuctor that constructs a Task with a task name
     * @param description The name of the constructed task
     */
    @JsonCreator
    public Task(@JsonProperty("description") String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Marks this task as done or not done.
     * @param done Whether the task is done or not
     */
    public void markTask(boolean done) {
        this.isDone = done;
    }

    @Override
    public String toString() {
        char marked = '\u2717';
        return "[" + (isDone ? marked : " ") + "] " + this.description;
    }
}