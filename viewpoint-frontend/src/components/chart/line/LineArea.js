import * as React from 'react';
import { LineChart } from '@mui/x-charts/LineChart';
import dayjs from 'dayjs';

const legendPlacement = {
    slotProps: {
      legend: {
        position: {
          vertical: 'top',
          horizontal: 'middle',
        },
        direction: 'row',
        itemGap: 20,
      },
    },
    margin: {
        bottom: 50,
    },
  };

const LineArea = (props) => {
    const {chartData} = props;
    const { chartSettings, rows, columns, data } = chartData;
    const { metrics, dimensions, xAxis, xAxisType } = chartSettings;

    // Устаноить тип оси
    const sortedKeys = columns.sort();
    const xColumn = columns.find(c => c.name === xAxis);

    // const getxAxisSettings = () => {
        const xAxisSettings = {
            data: sortedKeys.map(stringDate => new Date(stringDate)),
        }

        if (xAxisType === "Timestamp") {
            xAxisSettings["scaleType"] = 'time';
            xAxisSettings["tickMinStep"] = 3600 * 1000 * 24; // 24 h
            xAxisSettings["valueFormatter"] = (value) => (dayjs(value).format("YYYY-MM-DD"));
        } else if (xAxisType === "String") {
            xAxisSettings["scaleType"] = 'band';
        }
    // }

    const metric = metrics[0];

    const seriesSettings = Object.keys(data).map(rowKey => ({
        id: rowKey,
        label: Object.keys(data).length > 1 ? rowKey : metric.label,
        data: sortedKeys.map(date => data[rowKey][date])
    }))
    
    return (
        <LineChart
            xAxis={[{ ...xAxisSettings }]}
            series={[
                ...seriesSettings
            ]}
            {...legendPlacement}
            margin={{bottom: 70, left: 60}}
        />
    )
}

export default LineArea;