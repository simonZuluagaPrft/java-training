package srau.api.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Schedule {
    private String day;
    private List<Pair<Integer, Integer>> classes;

    public Schedule(String day) {
        this.day = day;
        this.classes = new ArrayList<>();
    }
}
