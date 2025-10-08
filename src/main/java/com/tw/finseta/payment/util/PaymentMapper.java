package com.tw.finseta.payment.util;

import org.mapstruct.*;
import com.tw.finseta.payment.model.Payment;
import com.tw.finseta.payment.dao.PaymentDAO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toModel(PaymentDAO dao);
    PaymentDAO toDAO(Payment model);

    List<Payment> toModelList(List<PaymentDAO> daoList);
}
