class Device
{
    constructor(status, replacementCost, supplierName, serialNumber)
    {
        this._status = status;
        this._replacementCost = replacementCost;
        this._supplierName = supplierName;
        this._serialNumber = serialNumber;
    }
    
    get status()
    {
        return this._status;
    }
    
    get replacementCost()
    {
        return this._replacementCost;
    }
    
    get supplierName()
    {
        return this._supplierName;
    }
    
    get serialNumber()
    {
        return this._serialNumber;
    }
    
    set replacementCost(replacementCost)
    {
        this._replacementCost = replacementCost;
    }
    
    set supplierName(supplierName)
    {
        this._supplierName = supplierName;
    }
    
    set serialNumber(serialNumber)
    {
        this._serialNumber = serialNumber;
    }
    
    enable()
    {
        this._status = 1;
    }
    
    disable()
    {
        this._status = 0;
    }
}

class VideoDevice extends Device
{
    constructor(status, replacementCost, supplierName, serialNumber, 
                resolution, type)
    {
        super(status, replacementCost, supplierName, serialNumber);
        this._resolution = resolution;
        this._type = type;
    }
    
    get resolution()
    {
        return this._resolution;
    }
    
    get type()
    {
        return this._type;
    }
    
    set resolution(resolution)
    {
        this._resolution = resolution;
    }
    
    set type(type)
    {
        this._type = type;
    }
}

class DiskDevice extends Device
{
    constructor(status, replacementCost, supplierName, serialNumber,
                size, transferRate)
    {
        super(status, replacementCost, supplierName, serialNumber);
        this._size = size;
        this._transferRate = transferRate;
    }
    
    get size()
    {
        return this._size;
    }
    
    get transferRate()
    {
        return this._transferRate;
    }
    
    set size(size)
    {
        this._size = size;
    }
    
    set transferRate(transferRate)
    {
        this._transferRate = transferRate;
    }
}

class HardDisk extends DiskDevice
{
    constructor(status, replacementCost, supplierName, serialNumber,
                size, transferRate, platterSize, numberOfPlatters)
    {
        super(status, replacementCost, supplierName, serialNumber, size, 
              transferRate);
        this._platterSize = platterSize;
        this._numberOfPlatters = numberOfPlatters;
    }
    
    get platterSize()
    {
        return this._platterSize;
    }
    
    get numberOfPlatters()
    {
        return this._numberOfPlatters;
    }
    
    set platterSize(platterSize)
    {
        this._platterSize = platterSize;
    }
    
    set numberOfPlatters(numberOfPlatters)
    {
        this._numberOfPlatters = numberOfPlatters;
    }
}

class SSD extends DiskDevice
{
      constructor(status, replacementCost, supplierName, serialNumber,
                size, transferRate, type, wearLeveling)
    {
        super(status, replacementCost, supplierName, serialNumber, size, 
              transferRate);
        this._type = type;
        this._wearLeveling = wearLeveling;
    }
    
    get type()
    {
        return this._type;
    }
    
    get wearLeveling()
    {
        return this._wearLeveling;
    }
    
    set type(type)
    {
        this._type = type;
    }
    
    set wearLeveling(wearLeveling)
    {
        this._wearLeveling = wearLeveling;
    }
}

//stores each object type
let videoDevices = [];
let hardDisks = [];
let ssds = [];

//starting at -1 to indicate no valid elements yet
let vdSelected = -1;
let hdSelected = -1;
let ssdSelected = -1;

function update(button)
{
    if(button.id === "vdUpdate")
    {
        //ensuring update is on a valid object
        if(vdSelected !== -1)
        {
            if(document.getElementById("vdStatus").checked === true){
                videoDevices[vdSelected].enable();
            }
            else{
                videoDevices[vdSelected].disable();
            }
            videoDevices[vdSelected].replacementCost = (document.getElementById(
                "vdReplacementCost").value);
            videoDevices[vdSelected].supplierName = document.getElementById(
                "vdSupplierName").value;
            videoDevices[vdSelected].serialNumber = document.getElementById(
                "vdSerialNumber").value;
            videoDevices[vdSelected].resolution = document.getElementById(
                "vdResolution").value;
            videoDevices[vdSelected].type = document.getElementById(
            "vdType").options[document.getElementById(
                "vdType").selectedIndex].value;
            alert("Successfully updated device!");
        }
    }
    else if(button.id === "hdUpdate")
    {
        if(hdSelected !== -1)
        {
            if(document.getElementById("hdStatus").checked === true){
                hardDisks[hdSelected].enable();
            }
            else{
                hardDisks[hdSelected].disable();
            }
            hardDisks[hdSelected].replacementCost = 
                document.getElementById("hdReplacementCost").value;
            hardDisks[hdSelected].supplierName = 
                document.getElementById("hdSupplierName").value;
            hardDisks[hdSelected].serialNumber = 
                document.getElementById("hdSerialNumber").value;
            hardDisks[hdSelected].size = document.getElementById("hdSize").value;
            hardDisks[hdSelected].transferRate = 
                document.getElementById("hdTransferRate").value;
            hardDisks[hdSelected].platterSize = 
                document.getElementById("hdPlatterSize").value;
            hardDisks[hdSelected].numberOfPlatters = 
                document.getElementById("hdNumberOfPlatters").value;
            alert("Successfully updated device!");
        }
    }
    else
    {
        if(ssdSelected !== -1)
        {
            if(document.getElementById("ssdStatus").checked === true){
                ssds[ssdSelected].enable();
            }
            else{
                ssds[ssdSelected].disable();
            }
            ssds[ssdSelected].replacementCost = 
                document.getElementById("ssdReplacementCost").value;
            ssds[ssdSelected].supplierName = 
                document.getElementById("ssdSupplierName").value;
            ssds[ssdSelected].serialNumber = 
                document.getElementById("ssdSerialNumber").value;
            ssds[ssdSelected].size = document.getElementById("ssdSize").value;
            ssds[ssdSelected].transferRate = 
                document.getElementById("ssdTransferRate").value;
            ssds[ssdSelected].type =      
                document.getElementById("ssdType"
                ).options[document.getElementById("ssdType"
                ).selectedIndex].value;
            ssds[ssdSelected].wearLeveling = 
                document.getElementById("ssdWearLeveling").options[
                document.getElementById("ssdWearLeveling").selectedIndex].value;
            alert("Successfully updated device!");
        }
    }
    
    
}

function createNew(button)
{
    if(button.id === "vdNew")
    {
        videoDevices.push(new VideoDevice(
            document.getElementById("vdStatus").checked,
            document.getElementById("vdReplacementCost").value,
            document.getElementById("vdSupplierName").value,
            document.getElementById("vdSerialNumber").value,
            document.getElementById("vdResolution").value,
            document.getElementById("vdType").options[
                document.getElementById("vdType").selectedIndex].value
        ));
        //moving to new element at end of array
        vdSelected = videoDevices.length - 1;
    }
    else if(button.id === "hdNew")
    {
        hardDisks.push(new HardDisk(
            document.getElementById("hdStatus").checked,
            document.getElementById("hdReplacementCost").value,
            document.getElementById("hdSupplierName").value,
            document.getElementById("hdSerialNumber").value,
            document.getElementById("hdSize").value,
            document.getElementById("hdTransferRate").value,
            document.getElementById("hdPlatterSize").value,
            document.getElementById("hdNumberOfPlatters").value
        )); 
        hdSelected = hardDisks.length - 1;
    }
    else
    {
        ssds.push(new SSD(
            document.getElementById("ssdStatus").checked,
            document.getElementById("ssdReplacementCost").value,
            document.getElementById("ssdSupplierName").value,
            document.getElementById("ssdSerialNumber").value,
            document.getElementById("ssdSize").value,
            document.getElementById("ssdTransferRate").value,
            document.getElementById("ssdType").options[
                document.getElementById("ssdType").selectedIndex].value,
            document.getElementById("ssdWearLeveling").options[
                document.getElementById("ssdWearLeveling").selectedIndex].value
        ));
        ssdSelected = ssds.length - 1;
    }
    
    alert("Successfully added new device!");
}

function previous(button)
{
    if(button.id === "vdPrev")
    {
        previousVD();
    }
    else if(button.id === "hdPrev")
    {
        previousHD();
    }
    else
    {
         previousSSD();   
    }
}

function previousVD()
{
    //only showing previous if not already showing the first
     if(vdSelected > 0)
     {
        --vdSelected;
        document.getElementById("vdStatus").checked = 
            videoDevices[vdSelected].status;
        document.getElementById("vdReplacementCost").value =
            videoDevices[vdSelected].replacementCost;
        document.getElementById("vdSupplierName").value =
            videoDevices[vdSelected].supplierName;
        document.getElementById("vdSerialNumber").value =
            videoDevices[vdSelected].serialNumber;
        document.getElementById("vdResolution").value = 
            videoDevices[vdSelected].resolution;
       if(videoDevices[vdSelected].type === "LCD")
       {
           document.getElementById("vdType").selectedIndex = 0;
           
       }
       else if(videoDevices[vdSelected].type === "LED")
       {
           document.getElementById("vdType").selectedIndex = 1;
       }
       else
       {
           document.getElementById("vdType").selectedIndex = 2;
       }
    }
}

function previousHD()
{
    if(hdSelected > 0)
    {
        --hdSelected;
        document.getElementById("hdStatus").checked = 
            hardDisks[hdSelected].status;
        document.getElementById("hdReplacementCost").value =
            hardDisks[hdSelected].replacementCost;
        document.getElementById("hdSupplierName").value =
            hardDisks[hdSelected].supplierName;
        document.getElementById("hdSerialNumber").value =
            hardDisks[hdSelected].serialNumber;
        document.getElementById("hdSize").value = 
            hardDisks[hdSelected].size;
        document.getElementById("hdTransferRate").value = 
            hardDisks[hdSelected].transferRate;
        document.getElementById("hdPlatterSize").value = 
            hardDisks[hdSelected].platterSize;
        document.getElementById("hdNumberOfPlatters").value = 
            hardDisks[hdSelected].numberOfPlatters;
    }
}

function previousSSD()
{
    if(ssdSelected > 0)
     {
        --ssdSelected;
        document.getElementById("ssdStatus").checked = 
            ssds[ssdSelected].status;
        document.getElementById("ssdReplacementCost").value =
            ssds[ssdSelected].replacementCost;
        document.getElementById("ssdSupplierName").value =
            ssds[ssdSelected].supplierName;
        document.getElementById("ssdSerialNumber").value =
            ssds[ssdSelected].serialNumber;
        document.getElementById("ssdSize").value = 
            ssds[ssdSelected].size;
        document.getElementById("ssdTransferRate").value = 
            ssds[ssdSelected].transferRate;
        if(ssds[ssdSelected].type === "Flash")
        {
           document.getElementById("ssdType").selectedIndex = 0;
        }
        else
        {
           document.getElementById("ssdType").selectedIndex = 1;
        }
      
        if(ssds[ssdSelected].wearLeveling === "Yes")
        {
           document.getElementById("ssdWearLeveling").selectedIndex = 0;
        }
        else
        {
           document.getElementById("ssdWearLeveling").selectedIndex = 1;
        } 
    }  
}
function next(button)
{
    if(button.id === "vdNext")
    {
        nextVD();
    }
    else if(button.id === "hdNext")
    {
        nextHD();
    }
    else
    {
        nextSSD();    
    }
}

function nextVD()
{
    //only displaying next if not already showing last
    if(vdSelected < videoDevices.length - 1)
    {
        ++vdSelected;
        document.getElementById("vdStatus").checked = 
            videoDevices[vdSelected].status;
        document.getElementById("vdReplacementCost").value =
            videoDevices[vdSelected].replacementCost;
        document.getElementById("vdSupplierName").value =
            videoDevices[vdSelected].supplierName;
        document.getElementById("vdSerialNumber").value =
            videoDevices[vdSelected].serialNumber;
        document.getElementById("vdResolution").value = 
            videoDevices[vdSelected].resolution;
       if(videoDevices[vdSelected].type === "LCD")
       {
           document.getElementById("vdType").selectedIndex = 0;
       }
       else if(videoDevices[vdSelected].type === "LED")
       {
           document.getElementById("vdType").selectedIndex = 1;
       }
       else
       {
           document.getElementById("vdType").selectedIndex = 2;
       }
    }
}

function nextHD()
{
    if(hdSelected < hardDisks.length - 1)
    {
        ++hdSelected;
        document.getElementById("hdStatus").checked = 
            hardDisks[hdSelected].status;
        document.getElementById("hdReplacementCost").value =
            hardDisks[hdSelected].replacementCost;
        document.getElementById("hdSupplierName").value =
            hardDisks[hdSelected].supplierName;
        document.getElementById("hdSerialNumber").value =
            hardDisks[hdSelected].serialNumber;
        document.getElementById("hdSize").value = 
            hardDisks[hdSelected].size;
        document.getElementById("hdTransferRate").value = 
            hardDisks[hdSelected].transferRate;
        document.getElementById("hdPlatterSize").value = 
            hardDisks[hdSelected].platterSize;
        document.getElementById("hdNumberOfPlatters").value = 
            hardDisks[hdSelected].numberOfPlatters;
    }
}

function nextSSD()
{
    if(ssdSelected < ssds.length - 1)
    {
        ++ssdSelected;
        document.getElementById("ssdStatus").checked = 
            ssds[ssdSelected].status;
        document.getElementById("ssdReplacementCost").value =
            ssds[ssdSelected].replacementCost;
        document.getElementById("ssdSupplierName").value =
            ssds[ssdSelected].supplierName;
        document.getElementById("ssdSerialNumber").value =
            ssds[ssdSelected].serialNumber;
        document.getElementById("ssdType").value = 
            ssds[ssdSelected].type;
        document.getElementById("ssdSize").value = 
            ssds[ssdSelected].size;
        document.getElementById("ssdTransferRate").value = 
            ssds[ssdSelected].transferRate;
        if(ssds[ssdSelected].type === "Flash")
        {
           document.getElementById("ssdType").selectedIndex = 0;
        }
        else 
        {
           document.getElementById("ssdType").selectedIndex = 1;
        }
        
        if(ssds[ssdSelected].wearLeveling === "Yes")
        {
           document.getElementById("ssdWearLeveling").selectedIndex = 0;
        }
        else 
        {
           document.getElementById("ssdWearLeveling").selectedIndex = 1;
        }
    }
}