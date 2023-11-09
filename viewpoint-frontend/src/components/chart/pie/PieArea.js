import * as React from 'react';
import { PieChart } from '@mui/x-charts/PieChart';
import { Box, height, width } from '@mui/system';

const PieArea = ({chartData, chartResult}) => {
    if (!chartResult) return;

    const { rows, coltypes } = chartResult;
    const { chartSettings } = chartData;
    const { metrics, dimensions } = chartSettings;

    const metric = metrics[0];

    const data = rows.map(((r, idx) => {
        let label = "";
        dimensions.map((c, idx) => {
            label += r[c.label] + ", ";
        })
        label = label.substring(0, label.length - 2)
        return { id: idx, value: r[metric.label], label: label}
    }))
    
    return (
        <PieChart
            series={[
            {
                data,
                arcLabel: (item) => `${item.value}`,
                    arcLabelMinAngle: 45,
            },
            ]}
            sx={{marginBottom: "30px"}}
        />
    )
}

export default PieArea;