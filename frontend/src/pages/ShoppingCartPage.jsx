import React from 'react';
import PageLayout from '../components/common/PageLayout.jsx';
import FullHeightContainer from '../components/common/FullHeightContainer.jsx';
import ShoppingCartCard from '../components/shoppingcartpage/ShoppingCartCard.jsx';
import { useShoppingCartPageHooks } from '../hooks/useShoppingCartPageHooks.jsx';

export default function ShoppingCartPage() {
    const {
        cartData,
        total,
        incrementItem,
        decrementItem,
        deleteItem,
        handleProceedToPayment
    } = useShoppingCartPageHooks();

    return (
        <PageLayout>
            <FullHeightContainer
                sx={{
                    display: 'flex',
                    width: '95vw',
                    height: '85vh',
                    backgroundColor: 'info.light',
                    borderRadius: '20px',
                    flexDirection: 'column',
                    alignItems: 'center',
                    padding: '20px'
                }}
            >
                <ShoppingCartCard
                    cartData={cartData}
                    totalAmount={total}
                    onDeleteItem={deleteItem}
                    onProceedToPayment={handleProceedToPayment}
                    onIncrementItem={incrementItem}
                    onDecrementItem={decrementItem}
                />
            </FullHeightContainer>
        </PageLayout>
    );
}
