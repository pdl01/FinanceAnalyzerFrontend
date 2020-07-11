export class StockPerformance {
    id: string;
    exchange: string;
    symbol: string;
    recordDate: number;
    recordDateAsString: string;
    sectors: string[];
    industries: string[];
    current: number;
    threedayperf: number;
    threedayopen: number;
    sevendayperf: number;
    sevendayopen: number;
    thirtydayperf: number;
    thirtydayopen: number;
}