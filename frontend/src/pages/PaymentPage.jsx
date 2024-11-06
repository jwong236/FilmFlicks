import React from 'react';
import { useLocation } from 'react-router-dom';
import PageLayout from '../components/common/PageLayout.jsx';
import PaymentInfoCard from '../components/paymentpage/PaymentInfoCard.jsx'
import { usePaymentPageHooks } from '../hooks/usePaymentPageHooks.jsx';

export default function PaymentPage() {
    const location = useLocation();
    const { cartData, total } = location.state || {};

    const {
        paymentInfo,
        setPaymentInfo,
        handlePlaceOrder,
        results,
    } = usePaymentPageHooks();

    return (
        <PageLayout>
            <PaymentInfoCard
                paymentInfo={paymentInfo}
                setPaymentInfo={setPaymentInfo}
                total={total}
                handlePlaceOrder={handlePlaceOrder}
                results={results}
                cartData={cartData}
                sx={{ height: '80vh', width: '30vw' }}
            />
        </PageLayout>
    );
}
