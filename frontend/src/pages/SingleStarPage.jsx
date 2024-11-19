import React from 'react';
import PageLayout from '../components/common/PageLayout.jsx';
import FullHeightContainer from '../components/common/FullHeightContainer.jsx';
import StarCard from '../components/common/StarCard.jsx';

export default function SingleStarPage() {
    return (
        <PageLayout>
            <FullHeightContainer
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    width: '30%',
                }}
            >
                <StarCard sx={{width: '90%'}}/>
            </FullHeightContainer>
        </PageLayout>
    );
}
