package test_task.com.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import test_task.com.model.Team;

import java.io.IOException;

public class TeamSerializer extends StdSerializer<Team> {

    private static final long serialVersionUID = 1L;

    protected TeamSerializer() {
        super(Team.class);
    }

    @Override
    public void serialize(Team team, JsonGenerator gen, SerializerProvider sp) throws IOException {
        gen.writeString(String.valueOf(team.getTeamID()));
    }
}
