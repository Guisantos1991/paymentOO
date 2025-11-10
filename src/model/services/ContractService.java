package model.services;

import model.entities.Contract;
import model.entities.Installments;
import model.entities.Installments;

import java.util.Calendar;
import java.util.Date;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;



    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract (Contract contract, Integer months) {
        Double basicQuota = contract.getTotalValue() / months;
        for (int i = 1; i <= months; i++) {
            Double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
            Double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
            Date dueDate = addMonths(contract.getDate(), i);
            contract.getInstallments().add(new Installments(dueDate, fullQuota));
        }
    }

    private Date addMonths(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }
}
