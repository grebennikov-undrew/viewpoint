import * as React from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
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

const BarArea = (props) => {
    const {chartData} = props;
    const { chartSettings, rows, columns, data } = chartData;
    const { metrics, dimensions, xAxis, xAxisType } = chartSettings;

    // Устаноить тип оси
    const sortedKeys = columns.sort();
    const xColumn = columns.find(c => c.name === xAxis);

    const xAxisSettings = {
        data: sortedKeys,
    };

    if (xAxisType === "Timestamp") {
        xAxisSettings["data"] = sortedKeys.map(stringDate => new Date(stringDate));
        xAxisSettings["scaleType"] = 'time';
        xAxisSettings["tickMinStep"] = 3600 * 1000 * 24; // 24 h
        xAxisSettings["valueFormatter"] = (value) => (dayjs(value).format("YYYY-MM-DD"));
    } else if (xAxisType === "String") {
        xAxisSettings["scaleType"] = 'band';
    }

    const metric = metrics[0];

    const seriesSettings = Object.keys(data).map(rowKey => ({
        id: rowKey,
        label: Object.keys(data).length > 1 ? rowKey : metric.label,
        data: sortedKeys.map(date => data[rowKey][date])
    }))
    
    return (
        <BarChart
            xAxis={[{ ...xAxisSettings }]}
            series={[
                ...seriesSettings
            ]}
            margin={{ top: 10, bottom: 20 }}
            {...legendPlacement}
        />
    )
}

export default BarArea;