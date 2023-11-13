import * as React from 'react';
import { LineChart } from '@mui/x-charts/LineChart';
import { Box, height, width } from '@mui/system';
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

const LineArea = ({chartData, chartResult}) => {
    if (!chartResult || !chartData) return;

    const { rows, columns, data } = chartResult;
    const { chartSettings, dataset } = chartData;
    const { metrics, dimensions, xAxis } = chartSettings;

    // Устаноить тип оси
    const sortedKeys = columns.sort();
    const xColumn = dataset.columns.find(c => c.name == xAxis);
    const xAxisSettings = {
        data: sortedKeys.map(stringDate => new Date(stringDate)),
    }
    if (xColumn["type"] === "Timestamp") {
        xAxisSettings["scaleType"] = 'time';
        xAxisSettings["tickMinStep"] = 3600 * 1000 * 24; // 24 h
        xAxisSettings["valueFormatter"] = (value) => (dayjs(value).format("YYYY-MM-DD"));
    } else if (xColumn["type"] === "String") {
        xAxisSettings["scaleType"] = 'band';
    }

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
            margin={{ top: 10, bottom: 20 }}
            {...legendPlacement}
            sx={{transform: "none"}}
            skipAnimation 
        />
    )
}

export default LineArea;