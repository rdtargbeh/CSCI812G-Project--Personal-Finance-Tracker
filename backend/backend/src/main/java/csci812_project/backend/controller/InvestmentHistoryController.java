package csci812_project.backend.controller;

import csci812_project.backend.dto.InvestmentHistoryDTO;
import csci812_project.backend.service.InvestmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/investment_history")
public class InvestmentHistoryController {

    @Autowired
    private InvestmentHistoryService investmentHistoryService;


    @PostMapping("/record")
    public ResponseEntity<String> recordInvestmentHistory(@RequestBody InvestmentHistoryDTO historyDTO) {
        investmentHistoryService.recordInvestmentHistory(historyDTO.getInvestmentId(), historyDTO.getCurrentValue());
        return ResponseEntity.ok("Investment history recorded and current value updated!");
    }

    @GetMapping("/{investmentId}")
    public ResponseEntity<List<InvestmentHistoryDTO>> getInvestmentHistory(@PathVariable Long investmentId) {
        return ResponseEntity.ok(investmentHistoryService.getInvestmentHistory(investmentId));
    }


    /**
     * ✅ Retrieve investment history for a given investment within a date range.
     */
    @GetMapping("/{investmentId}/filter")
    public ResponseEntity<List<InvestmentHistoryDTO>> getInvestmentHistoryByDateRange(
            @PathVariable Long investmentId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<InvestmentHistoryDTO> history = investmentHistoryService.getInvestmentHistoryByDateRange(investmentId, startDate, endDate);
        return ResponseEntity.ok(history);
    }

//    @PostMapping("/record/{investmentId}")
//    public ResponseEntity<Void> recordHistory(@PathVariable Long investmentId, @RequestBody InvestmentHistoryDTO historyDTO) {
//        investmentHistoryService.recordInvestmentsHistory(investmentId);
//        return ResponseEntity.ok().build();
//    }

}
