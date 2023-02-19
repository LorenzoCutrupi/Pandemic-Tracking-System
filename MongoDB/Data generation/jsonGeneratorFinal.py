from collections import OrderedDict
import json
import random, string
import datetime
import csv


personCounter = 0
vaccineBrand = ['Pfizer-BioNTech', 'Moderna', 'Johnson & Johnson Janssen', 'Sputnik V']
testType = ['Antigen tests', 'Molecular/PCR tests', 'Antibody']
authorized_centers_ids = ['c4ceUtvT', 'HPrj94aa', 'omKWu98X', 'ZvTJzMvG', 'oEEMSkZ3', 'PRfcJsn6', 'ieSviq2u', 'KtvGsZmf', 'VAk5kZ65', 'HfdsCoq2']
authorized_centers = [
    {
        'id' : 'c4ceUtvT',
        'name' : 'Policlinico di Modena',
        'address' : 'Via del Pozzo, 71, 41125 Modena MO',
        'type' : 'Policlinico',
        'department' : 'COVID-19 Unit'
    },
    {
        'id' : 'HPrj94aa',
        'name' : 'Centro MICI -Ospedale Sandro Pertini',
        'address' : 'Via dei Monti Tiburtini, 385, 00157 Roma RM',
        'type' : 'Ospedale',
        'department' : 'Reparto di pronto soccorso'
    },
    {
        'id' : 'omKWu98X',
        'name' : 'Humanitas Research Hospital',
        'address' : 'Via Alessandro Manzoni, 56, 20089 Rozzano MI',
        'type' : 'Research Hospital',
        'department' : 'COVID-19 Unit'
    },
    {
        'id' : 'ZvTJzMvG',
        'name' : 'IRCCS Policlinco San Donato',
        'address' : 'Piazza Edmondo Malan, 2, 20097 San Donato Milanese MI',
        'type' : 'Policlinico',
        'department' : 'Reparto di pronto soccorso'
    },
    {
        'id' : 'oEEMSkZ3',
        'name' : 'Ospedale Giuseppe Casati',
        'address' : 'Via Luigi Settembrini, 1, 20017 Rho MI',
        'type' : 'Ospedale',
        'department' : 'Reparto malattie infettive'
    },
    {
        'id' : 'PRfcJsn6',
        'name' : 'Ospedale Sacco',
        'address' : 'Via Baranzate, 8926, 20026 Novate Milanese MI',
        'type' : 'Ospedale',
        'department' : 'Reparto malattie infettive'
    },
    {
        'id' : 'ieSviq2u',
        'name' : 'HUB Vaccinale COV Mariano Comense',
        'address' : 'Via Don Sturzo, 48, 22066 Mariano Comense CO',
        'type' : 'HUB vaccinale',
        'department' : 'Centro vaccinazioni'
    },
    {
        'id' : 'KtvGsZmf',
        'name' : 'Centro vaccinale COVID-19 Meda',
        'address' : 'Via Cialdini, 154, 20821 Meda MB',
        'type' : 'HUB vaccinale',
        'department' : 'Centro vaccinazioni'
    },
    {
        'id' : 'VAk5kZ65',
        'name' : 'Centro vaccinale COVID-19 Meda',
        'address' : 'Via Cialdini, 154, 20821 Meda MB',
        'type' : 'HUB vaccinale',
        'department' : 'Centro vaccinazioni'
    },
    {
        'id' : 'HfdsCoq2',
        'name' : 'Azienda Ospedaliera Careggi di Firenze',
        'address' : 'Largo Giovanni Alessandro Brambilla, 3, 50134 Firenze FI',
        'type' : 'HUB vaccinale',
        'department' : 'Centro vaccinazioni'
    }
]

# Parses a .csv file and returns a list of dictionaries. Each dictionary contains a different row of the .csv file
def csvParser():
    with open('certificationRawData.csv', 'r') as file:
        csv_file = csv.DictReader(file)
        peopleDataRaw = {}
        peopleDataRaw['entries'] = []
        for row in csv_file:
            peopleDataRaw['entries'].append(dict(row))
    return peopleDataRaw

peopleDataRaw = csvParser()

#Random date generator for the vaccine production date
def randomDate():
	start_date = datetime.datetime(2021, 1, 1)
	end_date = datetime.datetime(2021, 11, 1)

	time_between_dates = end_date - start_date
	days_between_dates = time_between_dates.days
	random_number_of_days = random.randrange(days_between_dates)
	random_date = start_date + datetime.timedelta(days=random_number_of_days)

	return random_date

# Random somministration_date. NOTE that the vaccine's production date must be prior to the somministration date
def randomSomministrationDate(production_date):
	start_date = production_date
	end_date = datetime.datetime(2021, 11, 1)

	time_between_dates = end_date - start_date
	days_between_dates = time_between_dates.days

	random_number_of_days = random.randrange(days_between_dates)
	random_date = start_date + datetime.timedelta(days=random_number_of_days)
	return random_date


#Random alphanumeric 16-digits code generator for vaccines Ids and for tests Ids
def randomId():
	randomId = ''.join(random.choice(string.ascii_lowercase + string.digits) for _ in range(16))
	return randomId

#Random alphanumeric 24-digits code generator for certificate Ids
def randomCertificateId():
	randomCertificateId = ''.join(random.choice(string.ascii_lowercase + string.digits) for _ in range(24))
	return randomCertificateId

#Random alphanumeric 4-digits code generator for vaccination centers' Ids
def randomCenterId():
	randomCenterId = ''.join(random.choice(string.digits) for _ in range(3)).join(random.choice(string.ascii_lowercase) for _ in range(3))
	return randomCenterId

#Random alphanumeric 8-digits code generator for vaccine's lots
def randomLot():
	randomLot = ''.join(random.choice(string.ascii_lowercase + string.digits) for _ in range(3))
	return randomLot

# Generates a certificate
def generateCertificate():
	certificateList = OrderedDict()
	certificateList['id'] = randomCertificateId()
	certificateList['person'] = generatePerson(personCounter)
	certificateList['vaccination'] = []

	for vaccineIndex in range(random.randint(0, 3)):
		if vaccineIndex != 0:
			certificateList['vaccination'].append(generateVaccination(vaccineIndex))

	certificateList['tests'] = []
	for index in range(random.randint(0, 5)):
		if index != 0:
			certificateList['tests'].append(generateTest())

	certificateList['emergency_contact'] = generateEmergencyContact(personCounter)
	return certificateList


# Each person can have between 0 and 2 vaccinations
def generateVaccination(vaccineIndex):
	vaccination = OrderedDict()
	vaccination['vaccine_id'] = randomId()
	vaccination['authorized_center_id'] = random.choice(authorized_centers_ids)
	vaccination['brand'] = random.choice(vaccineBrand)
	vaccination['lot'] = randomLot()
	productionDate = randomDate()
	vaccination['production_date'] = productionDate.strftime("%Y-%m-%d")
	vaccination['somministration_date'] = randomSomministrationDate(productionDate).strftime("%Y-%m-%d")
	vaccination['doctors'] = []
	for index in range(random.randint(1, 2)):
		vaccination['doctors'].append(generateDoctor())
	vaccination['nurses'] = []
	for index in range(random.randint(1, 2)):
		vaccination['nurses'].append(generateNurse())
	vaccination['dose_number'] = vaccineIndex
	return vaccination


# Each person can have between 0 and 4 tests [ASSUMPTION]
def generateTest():
	test = OrderedDict()
	test['test_id'] = randomId()
	test['authorized_center_id'] = random.choice(authorized_centers_ids)
	test['type'] = random.choice(testType)
	test['date'] = randomDate().strftime("%Y-%m-%d")
	test['doctors'] = generateDoctor()
	test['nurses'] = generateNurse()
	test['result'] = random.choice(['true', 'false'])
	return test


# Generates a random person
def generatePerson(personCounter):
	person = OrderedDict()
	person['person_id'] = personCounter
	person['name'] = peopleDataRaw['entries'][personCounter]['\xef\xbb\xbfGivenName']
	person['surname'] = peopleDataRaw['entries'][personCounter]['Surname']
	person['birthdate'] = peopleDataRaw['entries'][personCounter]['Birthday']
	person['age'] = peopleDataRaw['entries'][personCounter]['Age']
	person['country'] = peopleDataRaw['entries'][personCounter]['CountryFull']
	person['city'] = peopleDataRaw['entries'][personCounter]['City']
	person['address'] = peopleDataRaw['entries'][personCounter]['StreetAddress']
	return person


# Generates a random emergency_contact
def generateEmergencyContact(personCounter):
	emergencyContactIndex = random.randint(1, 200)
	while emergencyContactIndex == personCounter:
		emergencyContactIndex = random.randint(1, 200)

	emergencyContact = OrderedDict()
	emergencyContact['name'] = peopleDataRaw['entries'][emergencyContactIndex]['\xef\xbb\xbfGivenName']
	emergencyContact['surname'] = peopleDataRaw['entries'][emergencyContactIndex]['Surname']
	emergencyContact['phone_number'] = peopleDataRaw['entries'][emergencyContactIndex]['TelephoneNumber']
	return emergencyContact


# Generates a list of 1 or 2 doctors. Each doctor_id ranges from 1000 to 1020
def generateDoctor():
    doctor = OrderedDict()
    doctor['doctor_id'] = random.randint(1000, 1020)
    return doctor


# Generates a list of 1 or 2 nurses. Each nurse_id ranges from 1060 to 2000
def generateNurse():
    nurse = OrderedDict()
    nurse['nurse_id'] = random.randint(1961, 2000)
    return nurse

def generateCenter(index):
    centerList = OrderedDict()
    selectedCenter = authorized_centers[index]
    centerList['authorized_center_id'] = selectedCenter['id']
    centerList['name'] = selectedCenter['name']
    centerList['address'] = selectedCenter['address']
    centerList['type_of_entity'] = selectedCenter['type']
    centerList['department_issuing_vaccine'] = selectedCenter['department']
    return centerList

# Create 200 entries of the JSON file. Each entry represents a person's certificate

with open('certificatesData.json', 'w') as outfile:
    for index in range(100):
        certificateDocument = OrderedDict()
        certificateDocument['certificate'] = generateCertificate()
        personCounter += 1
        json.dump(certificateDocument, outfile, ensure_ascii=False, indent=4)

    for index in range(10):
        centerDocument = OrderedDict()
        centerDocument['authorized_center'] = generateCenter(index)
        json.dump(centerDocument, outfile, ensure_ascii=False, indent=4)

with open('certificatesData.json', 'r') as outfile:
	line = outfile.readline()
	finalOut = ""
	while (line):
		first = line.split(':')
		if (first[0] == "                \"somministration_date\"" or
			first[0] == "                \"production_date\"" or
			first[0] == "                \"date\"" or
			first[0] == "            \"birthdate\"" ):
			print(first[1])
			second = "{\"$date\":" + first[1].split(',')[0] + "},\n"
			print(second)
			finalOut = finalOut + first[0] + ":" + second
		else: finalOut = finalOut + line
		line = outfile.readline()
	#open text file
	text_file = open("certificatesData.json", "w")

	#write string to file
	text_file.write(finalOut)

	#close file
	text_file.close()
