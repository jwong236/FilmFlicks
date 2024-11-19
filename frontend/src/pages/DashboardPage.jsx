import React, { useState } from 'react';
import DashboardLoginCard from "../components/dashboardpage/DashboardLoginCard.jsx";
import PageLayout from "../components/common/PageLayout.jsx";
import DashboardMainCard from "../components/dashboardpage/DashboardMainCard.jsx";

export default function DashboardPage(){

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [employeeName, setEmployeeName] = useState();


    return(
        <PageLayout showNavbar={false} sx={{
            display: 'flex',
            flexDirection: 'column'
        }}>
            {isLoggedIn ? (
                <DashboardMainCard employeeName={employeeName} />
            ) : <DashboardLoginCard setIsLoggedIn={setIsLoggedIn} setEmployeeName={setEmployeeName} />
            }
        </PageLayout>
    );
}
