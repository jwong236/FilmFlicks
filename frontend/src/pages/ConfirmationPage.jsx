import React from 'react';
import ConfirmationCard from "../components/confirmationpage/ConfirmationCard.jsx";
import PageLayout from "../components/common/PageLayout.jsx";

export default function ConfirmationPage() {
    return (
        <PageLayout
            sx={{
                display: 'flex',
                flexDirection: 'column',
            }}
        >
            <ConfirmationCard />
        </PageLayout>
    );
}
