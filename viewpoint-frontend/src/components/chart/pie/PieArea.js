import * as React from 'react';
import { PieChart } from '@mui/x-charts/PieChart';
import { Box } from '@mui/system';

const PieArea = ({chartData, chartResult}) => {
    if (!chartResult) return;

    const { rows, coltypes } = chartResult;
    const { chartSettings } = chartData;
    const { xColumns, aggFunction, groupBy } = chartSettings;

    const valueName = aggFunction + "(" + xColumns[0] + ")"

    const data = rows.map(((r, idx) => {
        let label = "";
        groupBy.map((c, idx) => {
            label += r[c] + ", ";
        })
        label = label.substring(0, label.length - 2)
        return { id: idx, value: r[valueName], label: label}
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
            
        />
    )
}

export default PieArea;