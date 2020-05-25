import {ISerializable} from "../api/ISerializable";

export class EnumType implements ISerializable {

    private readonly options:EnumTypeOptions;

    constructor(options:any) {
        this.options = options;
    }

    deserialize(buffer: any, registry: any): any {
    }

    serialize(value: any, buffer: any, registry: any): void {
    }

    size(value: any, registry: any): number {
        return 0;
    }

}

export class EnumTypeOptions {

}
