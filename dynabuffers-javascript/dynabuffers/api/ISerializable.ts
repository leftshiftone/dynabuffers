export interface ISerializable {

    size(value:any, registry:any):number;

    serialize(value:any, buffer:any, registry:any):void;

    deserialize(buffer:any, registry:any):any;

}
