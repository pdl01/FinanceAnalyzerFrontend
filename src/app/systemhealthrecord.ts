import { SystemHealthDailyStockCount } from './systemhealthdailystockcount';

export class SystemHealthRecord {
    recordDate: number;
    recordDateAsString: string;
    companiesWithoutNewsItemsInPast3Days: string[];
    companiesWithoutNewsItemsInPast7Days: string[];
    companiesWithoutNewsItemsInPast30Days: string[];
    companiesWithoutStockHistoriesInPast3Days: string[];
    companiesWithoutStockHistoriesInPast7Days: string[];
    totalNumberOfUserAnalyzedNewsItems: number;
    totalNumberOfUserUnanalyzedNewsItems: number;
    totalNumberOfSystemAnalyzedNewsItems: number;
    totalNumberOfSystemUnanalyzedNewsItems: number;
    totalNumberOfPositiveUserAnalyzedNewsItems: number;
    totalNumberOfNegativeUserAnalyzedNewsItems: number;
    totalNumberOfUnrelatedUserAnalyzedNewsItems: number;
    
    totalNumberOfPositiveSystemAnalyzedNewsItems: number;
    totalNumberOfNegativeSystemAnalyzedNewsItems: number;
    totalNumberOfUnrelatedSystemAnalyzedNewsItems: number;
    dailyStockCount: SystemHealthDailyStockCount[];
}