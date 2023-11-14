import React, { useState, useEffect } from 'react';
import GridLayout from "react-grid-layout";
import { Responsive as ResponsiveGridLayout } from "react-grid-layout";
import 'react-grid-layout/css/styles.css';
import RGL, { WidthProvider } from "react-grid-layout";

import Box from '@mui/system/Box';

import ChartArea from '../chart/ChartArea';

const ReactGridLayout = WidthProvider(RGL);

const DashboardLayot = (props) => {

    const { mode, dashboardData } = props;
    const { handleSettingsChange } = props;
    const { layout, charts } = dashboardData;

    const cursor = mode === "edit" ? "grab" : "default";

    return(
        <Box position={"relative"} width={"100%"}>
            <ReactGridLayout
                className="layout"
                layout={layout}
                cols={12}
                rowHeight={200}
                isBounded={true}
                onLayoutChange={(layout) => handleSettingsChange("layout", layout)}
                useCSSTransforms={true}
                isDraggable={mode === "edit"}
                isResizable={mode === "edit"}
                margin={[20,20]}
        >
            {charts.map((chartData) => {
                return <div key={chartData["id"]} style={{cursor: cursor}} ><ChartArea chartData={chartData}/></div>
            })}
            {/* <div key="a" ><ChartArea chartData={dashboardData[0]["chart"]} chartResult={dashboardData[0]} /></div>
            <div key="b" style={{backgroundColor: "gray"}}>b</div>
            <div key="c" style={{backgroundColor: "gray"}}>c</div> */}
            {/* {dashboardData && dashboardData.map(chartEntry => {
                const chartData = chartEntry["chart"];
                const chartResult = chartEntry;
                if (chartData)
                    return <ChartArea chartData={chartData} chartResult={chartResult} />
            })} */}
                {/* <div style={{ height: "400px", width: "100%"}}>
                    <ChartArea chartData={dashboardData[0]["chart"]} chartResult={dashboardData[0]} />
                </div> */}
            </ReactGridLayout>
        </Box>
    )
}

export default DashboardLayot;