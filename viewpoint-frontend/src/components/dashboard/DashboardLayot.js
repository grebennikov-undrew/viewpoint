import React, { useState, useEffect } from 'react';
import GridLayout from "react-grid-layout";
import { Responsive as ResponsiveGridLayout } from "react-grid-layout";
import 'react-grid-layout/css/styles.css';
import RGL, { WidthProvider } from "react-grid-layout";
import ClearIcon from '@mui/icons-material/Clear';
import HeightIcon from '@mui/icons-material/Height';

import Box from '@mui/system/Box';

import { httpRequest } from '../../service/httpRequest';
import ChartArea from '../chart/ChartArea';

const ReactGridLayout = WidthProvider(RGL);

const DashboardLayot = (props) => {
    const [ newCharts, setNewCharts ] = useState()

    const { mode, dashboardData } = props;
    const { handleLayoutChange } = props;
    const { layout, charts } = dashboardData;

    const cursor = mode === "edit" ? "grab" : "default";

    const onDelete = (key) => {
        const filteredCharts = charts.filter(chart => chart.id !== key);
        const newLayout = layout.filter(item => item.id !== `${key}`);
        handleLayoutChange(filteredCharts,newLayout);
    }

    const onChangeLayout = (layout) => {
        handleLayoutChange(charts,layout);
    }

    return(
        <Box position={"relative"} width={"100%"}>
            <ReactGridLayout
                className="layout"
                layout={layout}
                cols={12}
                rowHeight={200}
                isBounded={true}
                onLayoutChange={onChangeLayout}
                useCSSTransforms={true}
                isDraggable={mode === "edit"}
                isResizable={mode === "edit"}
                margin={[20,20]}
        >
            {charts.map((chartData) => {
                const itemKey = chartData["id"];
                return <div key={itemKey} style={{cursor: cursor}} >
                    <ChartArea chartData={chartData}/>
                    {mode === "edit" && <RemoveButton itemKey={itemKey} onDelete={onDelete} dashboardData={dashboardData}/>}
                    {mode === "edit" && <ResizeButton/>}
                </div>
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


const RemoveButton = ({itemKey, onDelete}) => {

    const removeStyle = {
        position: "absolute",
        right: "-16px",
        top: "-16px",
        cursor: "pointer",
        zIndex: 1000,
    };

    const onRemoveItem = () => {
        onDelete(itemKey);
    }

    return (
        <span
            className="remove"
            style={removeStyle}
            onClick={onRemoveItem}
            // onMouseUp={onRemoveItem}

        >
            <ClearIcon
                style={{
                    backgroundColor: "white",
                    borderRadius: "100%",
                    border: "2px solid #e0e0e0"
                }}
            />
        </span>
    )
}

const ResizeButton = () => {
    const resizeStyle = {
        position: "absolute",
        right: "-3px",
        bottom: "-6px",
        cursor: "pointer",
        transform: "rotate(315deg)"
    };

    return (<>
        <span style={resizeStyle}><HeightIcon/></span>
    </>);
}




export default DashboardLayot;