package com.syntacs.jobatm.WorkerService.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkerLocationHistoryDTO {
    private Long locationHistoryId;
    private Long workerId;
    private String location;
    private LocalDateTime timestamp;
}
