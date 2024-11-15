import React from 'react';
import PageLayout from '../components/common/PageLayout.jsx';
import FullHeightContainer from '../components/common/FullHeightContainer.jsx';
import ShoppingCartCard from '../components/shoppingcartpage/ShoppingCartCard.jsx';

export default function ShoppingCartPage() {


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
                <ShoppingCartCard />
            </FullHeightContainer>
        </PageLayout>
    );
}
