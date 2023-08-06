package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class ViewStatDto {

    @NotBlank
    String app;
    @NotBlank
    String uri;
    @NotNull
    Long hits;
}
/*ViewStats{
app	string
example: ewm-main-service
Название сервиса

uri	string
example: /events/1
URI сервиса

hits	integer($int64)
example: 6
Количество просмотров*/