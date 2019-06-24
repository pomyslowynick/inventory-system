package com.inventory_system.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.core.convert.ConversionException;

public class BigDecimalFieldPS extends CustomField<BigDecimal> {

    private static final long serialVersionUID = -5150605101121179296L;
    private TextField content;
    private NumberFormat numberFormat;
    private int recursions = 0;

    public BigDecimalFieldPS() {
        DecimalFormat numberFormat = (DecimalFormat) DecimalFormat.getNumberInstance(Locale
                .getDefault());
        numberFormat.setParseBigDecimal(true);

        this.numberFormat = numberFormat;

        init();
    }

    @Override
    protected BigDecimal generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(BigDecimal newPresentationValue) {

    }

    public BigDecimalFieldPS(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;

        init();
    }

    private void init() {
        content = new TextField();
        content.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = -4994220730867294055L;

            @Override
            public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
                if (recursions == 0) {
                    recursions++;
                    try {
                        if (event.getProperty().getValue() == null)
                            BigDecimalFieldPS.this.setValue(null);
                        else {
                            try {
                                BigDecimal bigDecimal = (BigDecimal) numberFormat.parse((String) event
                                        .getProperty().getValue());
                                BigDecimalFieldPS.this.setValue(bigDecimal);
                            } catch (com.vaadin.data.Property.ReadOnlyException | ConversionException
                                    | ParseException e) {
                            }
                            if (BigDecimalFieldPS.this.getValue() == null)
                                content.setValue(null);
                            else
                                content.setValue(numberFormat.format(BigDecimalFieldPS.this.getValue()));
                        }
                    } finally {
                        recursions--;
                    }
                }
            }
        });

        setImmediate(true);
    }

    @Override
    protected Component initContent() {
        return content;
    }

    @Override
    public Class<? extends BigDecimal> getType() {
        return BigDecimal.class;
    }

    @Override
    protected void setInternalValue(BigDecimal newValue) {
        super.setInternalValue(newValue);

        recursions++;
        try {
            if (newValue == null)
                content.setValue(null);
            else
                content.setValue(numberFormat.format(newValue));
        } finally {
            recursions--;
        }
    }

    @Override
    public void setImmediate(boolean immediate) {
        super.setImmediate(immediate);
        content.setImmediate(immediate);
    }

    @Override
    public void setSizeFull() {
        super.setSizeFull();
        content.setSizeFull();
    }

    @Override
    public void setSizeUndefined() {
        super.setSizeUndefined();
        content.setSizeUndefined();
    }

    @Override
    public void setWidth(String width) {
        super.setWidth(width);
        content.setWidth(width);
    }

    @Override
    public void setHeight(String height) {
        super.setHeight(height);
        content.setHeight(height);
    }

    public void selectAll() {
        content.selectAll();
    }

}