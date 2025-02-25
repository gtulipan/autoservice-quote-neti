package hu.neti.autoservice.quote.util;

import hu.neti.autoservice.quote.model.Customer;
import hu.neti.autoservice.quote.model.CustomerType;
import hu.neti.autoservice.quote.model.Part;
import hu.neti.autoservice.quote.model.QuoteDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Demo class for creating an offer.</p>
 * <p>When creating a market product, the data and products are available from a database,
 * and the calculation must be performed according to the Visitor Sample.</p>
 */
@Slf4j
@Component
public class QuoteCalculator {

    @Value("${quote.discount.total.amount.big}")
    int discountOnTotalAmountBig;

    @Value("${quote.discount.total.amount.medium}")
    int discountOnTotalAmountMedium;

    @Value("${quote.discount.vip.big}")
    int discountVipBig;

    @Value("${quote.discount.vip.medium}")
    int discountVipMedium;

    @Value("${quote.discount.vip.small}")
    int discountVipSmall;

    @Value("${quote.discount.loyal.percentage}")
    double discountLoyalPercentage;

    @Value("${quote.discount.total.amount.small}")
    int discountOnTotalAmountSmall;

    @Value("${quote.component.benefit.key.normal}")
    double componentBenefitKeyNormal;

    @Value("${quote.component.benefit.key.vip}")
    double componentBenefitKeyVip;

    @Value("${quote.labor.cost.percentage.multiplier}")
    double laborCostPercentageMultiplier;

    @Value("${quote.labor.cost.minimal}")
    int laborCostMinimal;

    public QuoteDetails calculateQuote(Customer customer, List<Part> parts) {
        QuoteDetails quoteDetails = new QuoteDetails();
        calculateLaborCost(parts, quoteDetails);
        calculateTotalCost(customer, parts, quoteDetails);
        return quoteDetails;
    }

    protected void calculateLaborCost(List<Part> parts, QuoteDetails details) {
        double totalPurchasePrice = parts.stream().mapToDouble(Part::getPurchasePrice).sum();
        double laborCost = Math.max(totalPurchasePrice * laborCostPercentageMultiplier, laborCostMinimal);
        log.debug("Calculated labor costs: {}", laborCost);
        details.setLaborFee(laborCost);
    }

    protected void calculateTotalCost(Customer customer, List<Part> parts, QuoteDetails details) {
        calculatePartCost(parts, details, componentBenefitKeyNormal);
        calculatePartTotal(details);
        if (customer.getCustomerType().equals(CustomerType.VIP)) {
            calculatePartTotalVIP(parts, details);
        } else if (customer.getCustomerType().equals(CustomerType.LOYAL)) {
            calculatePartTotalLoyal(details);
        }
        details.setTotal(details.getCoastOfParts() + details.getLaborFee());
        log.debug("Calculated total cost: {}.", (details.getTotal()));
    }

    protected void calculatePartCost(List<Part> parts, QuoteDetails details, double benefitKey) {
        details.setPartsCalculated(parts.stream()
                .map(part ->
                        Part.builder()
                                .name(part.getName())
                                .purchasePrice(part.getPurchasePrice() * benefitKey)
                                .build()
                ).toList());
        log.debug("Benefit key is: {}. The calculated costs of parts are: {}", benefitKey, details.getPartsCalculated());
    }

    protected void calculatePartTotal(QuoteDetails details) {
        details.setCoastOfParts(details.getPartsCalculated().stream()
                .mapToDouble(Part::getPurchasePrice)
                .sum());
        log.debug("REGULAR: Calculated cost of parts, the default price: {}", details.getCoastOfParts());
    }

    protected void calculatePartTotalVIP(List<Part> parts, QuoteDetails details) {
        calculatePartCost(parts, details, componentBenefitKeyVip);
        calculatePartTotal(details);
        double discount = 0;
        double partsAndLaborCost = details.getCoastOfParts() + details.getLaborFee();
        if (partsAndLaborCost > discountOnTotalAmountBig) {
            discount = discountVipBig;
        } else if (partsAndLaborCost > discountOnTotalAmountMedium) {
            discount = discountVipMedium;
        } else if (partsAndLaborCost > discountOnTotalAmountSmall) {
            discount = discountVipSmall;
        }
        details.setDiscountOfParts(discount);
        details.setCoastOfParts(details.getCoastOfParts() - discount);
        log.debug("VIP: Calculated discount: {} and cost of parts: {}.", discount, details.getCoastOfParts());
    }

    protected void calculatePartTotalLoyal(QuoteDetails details) {
        details.setDiscountOfParts(details.getCoastOfParts() * (1 - discountLoyalPercentage));
        details.setCoastOfParts(details.getCoastOfParts() * discountLoyalPercentage);
        log.debug("LOYAL: Calculated cost of parts: {}.", details.getCoastOfParts());
    }
}
