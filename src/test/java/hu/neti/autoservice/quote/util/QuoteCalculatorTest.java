package hu.neti.autoservice.quote.util;

import hu.neti.autoservice.quote.data.BuildTestData;
import hu.neti.autoservice.quote.model.Customer;
import hu.neti.autoservice.quote.model.CustomerType;
import hu.neti.autoservice.quote.model.Part;
import hu.neti.autoservice.quote.model.QuoteDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuoteCalculatorTest {

    @InjectMocks
    private QuoteCalculator quoteCalculator;

    @Mock
    private Customer customer;

    @Mock
    private List<Part> parts;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        quoteCalculator.discountOnTotalAmountBig = 1_000_000;
        quoteCalculator.discountOnTotalAmountMedium = 500_000;
        quoteCalculator.discountVipBig = 120_000;
        quoteCalculator.discountVipMedium = 50_000;
        quoteCalculator.discountVipSmall = 12_000;
        quoteCalculator.discountLoyalPercentage = 0.95;
        quoteCalculator.discountOnTotalAmountSmall = 200_000;
        quoteCalculator.componentBenefitKeyNormal = 1.12;
        quoteCalculator.componentBenefitKeyVip = 1.10;
        quoteCalculator.laborCostPercentageMultiplier = 0.3;
        quoteCalculator.laborCostMinimal = 10_000;

        customer = BuildTestData.buildCustomer(CustomerType.LOYAL);
        parts = BuildTestData.buildParts();
    }

    @Test
    void calculateQuote() {
        var quoteDetails = quoteCalculator.calculateQuote(customer, parts);
        assertEquals(quoteDetails.getTotal(), quoteDetails.getCoastOfParts() + quoteDetails.getLaborFee());
    }

    @Test
    void calculateLaborCost() {
        var details = new QuoteDetails();
        quoteCalculator.calculateLaborCost(parts, details);
        var expectedLaborCost = Math.max(parts.stream().mapToDouble(Part::getPurchasePrice).sum() * quoteCalculator.laborCostPercentageMultiplier, quoteCalculator.laborCostMinimal);
        assertEquals(expectedLaborCost, details.getLaborFee());
    }

    @Test
    void calculatePartCost_RegularAndLoyal() {
        var details = new QuoteDetails();
        quoteCalculator.calculatePartCost(parts, details, quoteCalculator.componentBenefitKeyNormal);
        var expected = new ArrayList<>(parts);
        expected.forEach(part -> part.setPurchasePrice(part.getPurchasePrice() * quoteCalculator.componentBenefitKeyNormal));
        assertEquals(expected, details.getPartsCalculated());
    }

    @Test
    void calculatePartCost_VIP() {
        var details = new QuoteDetails();
        quoteCalculator.calculatePartCost(parts, details, quoteCalculator.componentBenefitKeyVip);
        var expected = new ArrayList<>(parts);
        expected.forEach(part -> part.setPurchasePrice(part.getPurchasePrice() * quoteCalculator.componentBenefitKeyVip));
        assertEquals(expected, details.getPartsCalculated());
    }

    @Test
    void calculatePartTotal() {
        customer = BuildTestData.buildCustomer(CustomerType.REGULAR);
        var details = new QuoteDetails();
        details.setLaborFee(235_553.40);
        details.setCoastOfParts(879_399.36);
        quoteCalculator.calculatePartCost(parts, details, quoteCalculator.componentBenefitKeyNormal);
        quoteCalculator.calculatePartTotalLoyal(details);
        var expectedCalculatePartTotal = details.getPartsCalculated().stream().mapToDouble(Part::getPurchasePrice).sum() * quoteCalculator.discountLoyalPercentage;
        var expectedCalculatePartTotalBD = BigDecimal.valueOf(expectedCalculatePartTotal).setScale(2, RoundingMode.HALF_UP);
        var actualCalculatePartTotalBD = BigDecimal.valueOf(details.getCoastOfParts()).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expectedCalculatePartTotalBD.doubleValue(), actualCalculatePartTotalBD.doubleValue());
    }

    @Test
    void calculatePartTotalVIP() {
        customer = BuildTestData.buildCustomer(CustomerType.VIP);
        var details = new QuoteDetails();
        details.setLaborFee(235_553.40);
        quoteCalculator.calculatePartTotalVIP(parts, details);
        var expectedDiscount = quoteCalculator.discountVipBig;
        assertEquals(expectedDiscount, details.getDiscountOfParts());
    }

    @Test
    void calculatePartTotalLoyal() {
        customer = BuildTestData.buildCustomer(CustomerType.LOYAL);
        var details = new QuoteDetails();
        details.setLaborFee(235_553.40);
        details.setCoastOfParts(879_399.36);
        quoteCalculator.calculatePartCost(parts, details, quoteCalculator.componentBenefitKeyNormal);
        quoteCalculator.calculatePartTotalLoyal(details);
        var expectedCalculatePartTotal = details.getPartsCalculated().stream().mapToDouble(Part::getPurchasePrice).sum() * quoteCalculator.discountLoyalPercentage;
        var expectedCalculatePartTotalBD = BigDecimal.valueOf(expectedCalculatePartTotal).setScale(2, RoundingMode.HALF_UP);
        var actualCalculatePartTotalBD = BigDecimal.valueOf(details.getCoastOfParts()).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expectedCalculatePartTotalBD.doubleValue(), actualCalculatePartTotalBD.doubleValue());
    }
}