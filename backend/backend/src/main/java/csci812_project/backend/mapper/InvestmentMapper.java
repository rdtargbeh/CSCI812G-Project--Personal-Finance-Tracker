package csci812_project.backend.mapper;

import csci812_project.backend.dto.InvestmentDTO;
import csci812_project.backend.entity.Investment;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.InvestmentType;
import org.springframework.stereotype.Component;

@Component
public class InvestmentMapper {

    public InvestmentDTO toDTO(Investment investment) {
        if (investment == null) return null;

        InvestmentDTO dto = new InvestmentDTO();
        dto.setInvestmentId(investment.getInvestmentId());
        dto.setUserId(investment.getUser().getUserId());
        dto.setInvestmentType(investment.getInvestmentType());
        dto.setAssetName(investment.getAssetName());
        dto.setAmountInvested(investment.getAmountInvested());
        dto.setPerformance(investment.getPerformance());
        dto.setCurrentValue(investment.getCurrentValue());
        dto.setPurchaseDate(investment.getPurchaseDate());
        dto.setLastUpdated(investment.getLastUpdated());
        dto.setDeleted(investment.isDeleted());
        dto.setDateCreated(investment.getDateCreated());

        return dto;
    }

    public Investment toEntity(InvestmentDTO dto, User user) {
        if (dto == null) return null;

        Investment investment = new Investment();
        investment.setUser(user);
        investment.setInvestmentType(dto.getInvestmentType());
        investment.setAssetName(dto.getAssetName());
        investment.setAmountInvested(dto.getAmountInvested());
        investment.setCurrentValue(dto.getCurrentValue());
        investment.setPurchaseDate(dto.getPurchaseDate());
        return investment;
    }

}

