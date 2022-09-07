import { Location } from './location';

export class WorkGroup{
    workGroupId: number;
    name: string;
    disciplineId: number;
    clubId: number;
    limitOfPlaces: number;
    startingDate: string;
    endDate: string;
    locationDto: Location;
    monthlyCost: number;
    bankAccountNumber: string;
}
